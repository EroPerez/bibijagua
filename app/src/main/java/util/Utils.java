/*
 * Copyright 2013-2014 Ludwig M Brinckmann
 * Copyright 2014, 2015 devemux86
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package util;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utility functions that can be used across different
 * activities.
 */
public final class Utils {

	private Utils() {
		throw new IllegalStateException();
	}

	/**
	 * Copia un fichero empaquetado con el programa en el directorio 'res/raw' a
	 * un directorio del almacenamiento externo. Si el directorio no existe se
	 * crea
	 *
	 * @param resid
	 *            int id del recuros del fichero que se quiere copiar. Debe
	 *            estar guardado en el directorio 'res/raw' del proyecto
	 * @param filename
	 *            String
	 *
	 * @return int 1 si todo va bien, 0 o negativo en caso contrario
	 */

	public static File copyFileToSdcard(final Context c, int resid,
			String filename, String filePath) {

		Log.d("Bibijagua", "copyFileToSdcard()");

		File dir = new File(filePath);
		if (!dir.exists()) {
			Log.d("Bibijagua", "Creando directorio " + dir.getAbsolutePath());
			if (!dir.mkdir()) {
				Log.d("Bibijagua",
						"Error, can't create directory "
								+ dir.getAbsolutePath());
				return null;
			}

		}

		File file = new File(filePath, filename);

		try {
			InputStream is = c.getResources().openRawResource(resid);
			OutputStream os = new FileOutputStream(file);

			byte[] buffer = new byte[512 * 1024];
			int read;
			while ((read = is.read(buffer)) != -1) {
				os.write(buffer, 0, read);
			}
			is.close();
			os.close();

		} catch (IOException e) {

			Log.w("Bibijagua", "Error writing " + file, e);
			return null;
		}

		return file;
	}

	public static boolean fileExist(String filename, String filePath) {

		File myFile = new File(filePath, filename);

		return myFile.exists();
	}

	public static boolean copyFile(File source, File dest) {
		try {
			// Declaration et ouverture des flux
			java.io.FileInputStream sourceFile = new java.io.FileInputStream(
					source);

			try {
				java.io.FileOutputStream destinationFile = null;

				try {
					destinationFile = new FileOutputStream(dest);

					// Lecture par segment de 0.5Mo
					byte buffer[] = new byte[512 * 1024];
					int nbLecture;

					while ((nbLecture = sourceFile.read(buffer)) != -1) {
						destinationFile.write(buffer, 0, nbLecture);
					}
				} finally {
					destinationFile.close();
				}
			} finally {
				sourceFile.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false; // Erreur
		}

		return true; // Rsultat OK
	}

	/**
	 * Dplace le fichier source dans le fichier rsultat
	 */
	public static boolean moveFile(File source, File destination) {
		if (!destination.exists()) {
			// On essaye avec renameTo
			boolean result = source.renameTo(destination);
			if (!result) {
				// On essaye de copier
				result = true;
				result &= copyFile(source, destination);
				if (result)
					result &= source.delete();

			}
			return (result);
		} else {
			// Si le fichier destination existe, on annule ...
			return (false);
		}
	}

}
