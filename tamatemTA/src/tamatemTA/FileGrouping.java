package tamatemTA;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileGrouping {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Please provide the path to the folder as a command-line argument.");
			return;
		}

		String folderPath = args[0];
		groupFilesIntoSubfolders(folderPath);
	}

	private static void groupFilesIntoSubfolders(String folderPath) {
		File folder = new File(folderPath);
		File[] files = folder.listFiles();

		if (files == null) {
			System.out.println("Invalid folder path: " + folderPath);
			return;
		}

		for (File file : files) {
			if (file.isFile() && file.getName().endsWith(".txt")) {
				String fileName = file.getName();
				String language = fileName.substring(0, fileName.indexOf("-"));
				String subfolderPath = folderPath + File.separator + language;

				File subfolder = new File(subfolderPath);
				if (!subfolder.exists()) {
					boolean created = subfolder.mkdir();
					if (!created) {
						System.out.println("Failed to create subfolder: " + subfolderPath);
						continue;
					}
				}

				Path source = file.toPath();
				Path destination = Path.of(subfolderPath, fileName);

				try {
					Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					System.out.println("Failed to move file: " + fileName);
					// e.printStackTrace();
				}
			}
		}

		System.out.println("File grouping completed.");
	}
}
