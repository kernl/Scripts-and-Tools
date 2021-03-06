/*	Copyright (C) 2009	Fernando Alexandre

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package data;

import gui.FileLabel;
import gui.MainFrame;

import java.io.File;

/**
 * Represents an action to be undone.
 * 
 * @author Fernando Alexandre
 */
public class UndoAction extends Action {

	/**
	 * Creates an UndoAction.
	 * 
	 * @param currentCounter
	 *            Counter value when the action happened.
	 * @param originalFile
	 *            Original file name.
	 * @param modifiedFile
	 *            Modified file name.
	 * @param label
	 *            Points to the FileLabel it is at.
	 */
	public UndoAction(int currentCounter, String originalFile,
			String modifiedFile, FileLabel label) {
		super(currentCounter, originalFile, modifiedFile, label);
	}

	/**
	 * Creates an UndoAction.
	 * 
	 * @param action
	 *            Action to be undone.
	 */
	public UndoAction(IAction action) {
		super(action.getCounter(), action.getOriginalFile(), action
				.getModifiedFile(), action.getLabel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see data.Action#apply()
	 */
	@Override
	public boolean apply() {
		String filePath = MainFrame.WORKING_DIR
				+ MainFrame.getSystemPathSymbol();
		File old_file = new File(filePath + getModifiedFile());
		File new_file = new File(filePath + getOriginalFile());

		// Rename file
		if (!new_file.exists()) {
			boolean success = old_file.renameTo(new_file);
			if (!success)
				MainFrame.setStatusMessage(MainFrame.ERR_RENAME);
			else {
				MainFrame.setStatusMessage(MainFrame.SUCCESS_MSG(
						getModifiedFile(), getOriginalFile()));
				getLabel().setFileName(getOriginalFile());
				MainFrame.decCounter();
				return true;
			}
		} else
			MainFrame.setStatusMessage(MainFrame.ERR_EXISTS(getOriginalFile()));
		return false;
	}
}
