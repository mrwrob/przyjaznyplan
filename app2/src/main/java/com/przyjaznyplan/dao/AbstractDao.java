/*
 * Copyright (c) 2016. Wydział Elektroniki, Telekomunikacji i Informatyki, Politechnika Gdańska
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or   (at your option) any later version.
 *
 * Copy of GNU General Public License is available at http://www.gnu.org/licenses/gpl-3.0.html
 */

package com.przyjaznyplan.dao;





import android.database.sqlite.SQLiteDatabase;

import com.przyjaznyplan.interfaces.DaoSql;

/**
 * Created by Chris on 10/24/2014.
 */

public abstract class AbstractDao<T> implements DaoSql<T>{

    protected SQLiteDatabase db;

    public AbstractDao(SQLiteDatabase db){this.db=db;}



}
