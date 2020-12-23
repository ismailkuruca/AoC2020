package com.ismailkuruca.aoc_2020;

import java.util.Objects;

class LinkedList {
    private LinkedList previous = this;
    LinkedList next = this;

    final Integer value;

    LinkedList(Integer value) {
        this.value = value;
    }

    LinkedList remove() {
        previous.next = next;
        next.previous = previous;
        return this;
    }

    void insert(LinkedList node) {
        next.previous = node;
        node.previous = this;
        node.next = next;
        this.next = node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedList that = (LinkedList) o;
        return Objects.equals(value, that.value);
    }
}