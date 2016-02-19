/*
 * Copyright (c) 2016. Wydział Elektroniki, Telekomunikacji i Informatyki, Politechnika Gdańska
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 3 of the License, or   (at your option) any later version.
 *
 * Copy of GNU General Public License is available at http://www.gnu.org/licenses/gpl-3.0.html
 */
package br.com.thinkti.android.filechooser;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.thinkti.android.filechooser.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class FileChooser extends ListActivity {
	private File currentDir;
	private FileArrayAdapter adapter;
	private FileFilter fileFilter;
	private File fileSelected;
	private ArrayList<String> extensions;
    private String fileStartPath;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			if (extras.getStringArrayList("filterFileExtension") != null) {
				extensions = extras.getStringArrayList("filterFileExtension");
				fileFilter = new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						return (pathname.getName().contains(".")?extensions.contains(pathname.getName().substring(pathname.getName().lastIndexOf("."))):true);
					}
				};
			}
            if (extras.get("fileStartPath") != null) {
                fileStartPath = extras.get("fileStartPath").toString();
                currentDir = new File(fileStartPath);
            }else {
                currentDir = new File("/");
                fileStartPath = "/";
            }
		} else {
            currentDir = new File("/");
            fileStartPath = "/";
        }
		fill(currentDir);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        	if ((currentDir.getAbsolutePath().equals(fileStartPath)) || (currentDir.getParentFile() == null)) {
                finish();
        	} else {
                currentDir = currentDir.getParentFile();
                fill(currentDir);
        	}
            return false;
        }
        return super.onKeyDown(keyCode, event);
	}

	private void fill(File f) {
		File[] dirs = null;
		if (fileFilter != null)
			dirs = f.listFiles(fileFilter);
		else 
			dirs = f.listFiles();
			
		this.setTitle(getString(R.string.currentDir) + ": " + f.getName());
		List<Option> dir = new ArrayList<Option>();
		List<Option> fls = new ArrayList<Option>();
		try {
			for (File ff : dirs) {
				if (ff.isDirectory() && !ff.isHidden())
					dir.add(new Option(ff.getName(), getString(R.string.folder), ff
							.getAbsolutePath(), true, false));
				else {
					if (!ff.isHidden())
						fls.add(new Option(ff.getName(), getString(R.string.fileSize) + ": "
								+ ff.length(), ff.getAbsolutePath(), false, false));
				}
			}
		} catch (Exception e) {

		}
		Collections.sort(dir);
		Collections.sort(fls);
		dir.addAll(fls);
		if (!f.getName().equalsIgnoreCase("")) {
			if (f.getParentFile() != null) dir.add(0, new Option("..", getString(R.string.parentDirectory), f.getParent(), false, true));
		}
		adapter = new FileArrayAdapter(FileChooser.this, R.layout.file_view,
				dir);
		this.setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Option o = adapter.getItem(position);
		if (o.isFolder() || o.isParent()) {			
			currentDir = new File(o.getPath());
			fill(currentDir);
		} else {
			//onFileClick(o);
			fileSelected = new File(o.getPath());
			Intent intent = new Intent();
			intent.putExtra("fileSelected", fileSelected.getAbsolutePath());
			setResult(Activity.RESULT_OK, intent);
			finish();
		}		
	}

	private void onFileClick(Option o) {
		Toast.makeText(this, "File Clicked: " + o.getName(), Toast.LENGTH_SHORT)
				.show();
	}
		
}