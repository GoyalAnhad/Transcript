import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

    class file_choose {

    // Opens a file chooser dialog to let the user select an input video file.
    public static String chooseInputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Input Video File");
        
        // Optional: filter to display only common video file formats.
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Video Files", "mp4", "avi", "mov", "mkv");
        fileChooser.setFileFilter(filter);
        
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }

    // Creates an output file path by appending "_audio.wav" to the input file name.
    public static String getOutputFilePath(String inputFilePath) {
        if (inputFilePath != null) {
            int dotIndex = inputFilePath.lastIndexOf('.');
            String baseName = (dotIndex > 0) ? inputFilePath.substring(0, dotIndex) : inputFilePath;
            return baseName + "_audio.wav";
        }
        return null;
    }
}
