package com.aries.readWriteLock;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest<T> {
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();
    private int capital;
    private Object[] elements;
    private int length;

    public ReadWriteLockTest() {
        this.capital = 3;
        this.elements = new Object[this.capital];
        this.length = 0;

    }

    private void reSize() {
        this.capital = this.capital * 2;
        Object[] newElements = Arrays.copyOf(this.elements, this.capital);
        this.elements = newElements;
    }

    public Boolean isEmpty() {
        return this.length == 0;
    }

    public T add(T data) {
        int index = this.length;
        final Lock lock = this.writeLock;
        try {
            lock.lockInterruptibly();
            if (this.length >= this.capital) {
                reSize();
            }
            this.elements[index] = data;
            this.length++;
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (index < 0 || index >= this.length - 1) {
            throw new RuntimeException("下标越界");
        }
        final Lock lock = this.writeLock;
        T oldValue = null;
        try {
            lock.lockInterruptibly();
            oldValue = (T) this.elements[index];

            if (index == this.capital - 1) {
                this.length--;
            } else {
                int num = this.capital - 1 - index;
                for (int i = 0; i < num; i++) {
                    this.elements[index] = this.elements[index + 1];
                    index++;
                }
            }
            this.length--;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return oldValue;
    }

    private int getIndex(T data) {
        int index = 0;
        for (Object forData : this.elements) {
            if (forData.hashCode() != data.hashCode()) {
                index++;
                continue;
            }
            break;
        }
        return index;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index > this.length - 1) {
            throw new RuntimeException("下标越界");
        }

        final Lock lock = this.readLock;
        try {
            lock.lockInterruptibly();
            T data = (T) this.elements[index];
            return data;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Object data : this.elements) {
            builder.append(data.toString());
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        ReadWriteLockTest<String> lockTest = new ReadWriteLockTest<>();
        lockTest.add("A");
        lockTest.add("B");
        lockTest.add("C");
        lockTest.add("D");
        lockTest.add("E");
        lockTest.add("F");
        lockTest.remove(0);
        lockTest.remove(3);
        lockTest.add("G");
        System.out.println(lockTest.get(0) + "  " + lockTest.get(1) + lockTest.get(2) + "  " + lockTest.get(3));
        System.out.println(lockTest.get(4));
        System.out.println(lockTest.toString());
    }
}
