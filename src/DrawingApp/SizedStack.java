/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DrawingApp;

import java.util.Stack;

/**
 *
 * @author Aditi
 */
public class SizedStack<T> extends Stack<T> {

private final int maxSize;

public SizedStack(int size) {
    super();
    this.maxSize = size;
}

@Override
public Object push(Object object) {
    while (this.size() > maxSize) {
        this.remove(0);
    }
    return super.push((T) object);
}
}
