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

import java.io.File;

import gui.FileLabel;
import gui.MainFrame;

/**
 * Represents an action to be done again.
 * 
 * @author Fernando Alexandre
 */
public class RedoAction extends Action {

	/**
	 * Creates a RedoAction.
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
	public RedoAction(int currentCounter, String originalFile,
			String modifiedFile, FileLabel label) {
		super(currentCounter, originalFile, modifiedFile, label);
	}

	/**
	 * Creates a RedoAction.
	 * 
	 * @param action
	 *            Action to be done again.
	 */
	public RedoAction(IAction action) {
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
		FileLabel curr = getLabel();
		String newFileName = MainFrame.getFileName(curr.getExtension());

		String filePath = MainFrame.WORKING_DIR
				+ MainFrame.getSystemPathSymbol();
		File old_file = new File(filePath + curr.getFileName());
		File new_file = new File(filePath + newFileName);

		// Rename file
		if (!new_file.exists()) {
			boolean success = old_file.renameTo(new_file);
			if (!success)
				MainFrame.setStatusMessage(MainFrame.ERR_RENAME);
			else {
				MainFrame.setStatusMessage(MainFrame.SUCCESS_MSG(curr
						.getFileName(), newFileName));
				curr.setFileName(newFileName);
				MainFrame.incCounter();
				return true;
			}
		} else
			MainFrame.setStatusMessage(MainFrame.ERR_EXISTS(newFileName));
		return false;
	}
}
