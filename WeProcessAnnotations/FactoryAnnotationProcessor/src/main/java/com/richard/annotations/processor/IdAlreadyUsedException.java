package com.richard.annotations.processor;

/**
 * Created by U0165547 on 4/22/2015.
 */

import com.richard.annotations.Factory;

/**
 * This Exception will be thrown to indicate that the the given {@link Factory#id()} is already used
 * by another {@link FactoryAnnotatedClass}
 *
 * @author Hannes Dorfmann
 */
public class IdAlreadyUsedException extends Exception {

    private FactoryAnnotatedClass existing;

    public IdAlreadyUsedException(FactoryAnnotatedClass existing) {
        this.existing = existing;
    }

    public FactoryAnnotatedClass getExisting() {
        return existing;
    }
}