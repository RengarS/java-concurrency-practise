package com.aries.future.copyOnWrite;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class AriesCopyOnWriteList<T> {
    private volatile Object[] elements;
    private int length = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public AriesCopyOnWriteList() {
        this.elements = new Object[0];
    }

    public Object[] getElements() {
        return this.elements;
    }

    public void setElements(Object[] elements) {
        this.elements = elements;
    }

    /**
     * 添加
     *
     * @param data
     * @return
     */
    public Boolean add(T data) {
        final ReentrantLock lock = this.lock;
        try {
            lock.lockInterruptibly();
            Object[] newElements = Arrays.copyOf(getElements(), length + 1);
            newElements[length] = data;
            this.length++;
            setElements(newElements);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return false;
    }

    /**
     * 根据下标获取
     *
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index >= this.length || index < 0) {
            throw new RuntimeException("下标越界");
        }
        return (T) getElements()[index];
    }

    /**
     * 根据下标移除
     *
     * @param index
     * @return
     */
    public T remove(int index) {

        if (index >= this.length || index < 0) {
            throw new RuntimeException("下标越界");
        }

        final ReentrantLock lock = this.lock;
        T oldValue = get(index);
        try {
            lock.lockInterruptibly();

            Object[] newElement = new Object[this.length - 1];
            //判断移除元素的位置
            int movedIndex = this.length - 1 - index;

            if (movedIndex == 0) {
                //如果移除的最后一个元素，就直接申请一个比元数组小1的新数组，然后从0复制，这样最后一个元素就被舍弃了
                System.arraycopy(getElements(), 0, newElement, 0, this.length - 1);
            } else {
                //如果移除的元素的不是最后一个，就先复制0到被移出元素的前一个元素（先复制前一半）
                System.arraycopy(getElements(), 0, newElement, 0, index);
                //复制后一半     从被移出元素的后一位一直到最后。即：原数组的index+1位到末尾，新数组的index位到末尾
                System.arraycopy(getElements(), index + 1, newElement, index, movedIndex);
            }
            this.length--;
            setElements(newElement);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return oldValue;
    }

    public static void main(String[] args) {
        AriesCopyOnWriteList<String> list = new AriesCopyOnWriteList<>();
        list.add("A");
        list.add("B");
        list.remove(1);
        System.out.println(list.get(0) + "    " + list.get(1));
    }
}
