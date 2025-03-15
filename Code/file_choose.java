import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

    class file_choose {

    // Displays a file chooser dialog and returns the selected file's absolute path.
    public String chooseInputFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Input Video File");
        
        // Filter for common video file types.
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Video Files", "mp4", "avi", "mov", "mkv");
        fileChooser.setFileFilter(filter);
        
        int userSelection = fileChooser.showOpenDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        }
        return null;
    }
    
    // Generates an output file path by appending "_audio.wav" to the base name of the input file.
    public String getOutputFilePath(String inputFilePath) {
        if (inputFilePath != null) {
            int dotIndex = inputFilePath.lastIndexOf('.');
            String baseName = (dotIndex > 0) ? inputFilePath.substring(0, dotIndex) : inputFilePath;
            return baseName + "_audio.wav";
        }
        return null;
    }
}