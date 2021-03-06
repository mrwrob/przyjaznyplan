/*
 * Copyright (c) 2016. Wydział Elektroniki, Telekomunikacji i Informatyki, Politechnika Gdańska
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or   (at your option) any later version.
 *
 * Copy of GNU General Public License is available at http://www.gnu.org/licenses/gpl-3.0.html
 */

package com.przyjaznyplan.models;

/**
 * Created by chris on 28.12.14.
 */
public enum TypyWidokuAktywnosci {

    big("duzy"),
    medium("sredni"),
    small("maly");

    private final String name;

    private TypyWidokuAktywnosci(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public static TypyWidokuAktywnosci getEnum(String s){
        if(s.equals(big.toString()))
                return big;
        if(s.equals(medium.toString()))
            return medium;
        if(s.equals(small.toString()))
            return small;

        return null;
    }

    public String toString() {
        return name;
    }

}
