package metro.app;

import java.io.*;

public class Test2 {
	public static void main(String [] args){
		File file = new File("D://TrainPhoto2//");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("filelist.txt")));
			if(file.isDirectory()){
				File [] files = file.listFiles();
				
				for(int i = 0; i < files.length; i++){
					bw.write(files[i].getName().substring(0, 1)+" "+files[i].getName());
					bw.newLine();
					bw.flush();
				}
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
