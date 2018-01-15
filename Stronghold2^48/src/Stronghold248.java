import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import com.scicraft.seedfinder.Biome;
import com.scicraft.seedfinder.biomeGenerator;
import com.scicraft.seedfinder.strongholdFinder;
import com.scicraft.seedfinder.xzPair;

public class Stronghold248 {
	public static void main(String[] args) {
		List<Biome> biomeArrayList = new ArrayList<Biome>();	
		for (int i = 0; i < Biome.biomes.length; i++) {
			if ((Biome.biomes[i] != null) && (Biome.biomes[i].type.value1 > 0f)) {
				biomeArrayList.add(Biome.biomes[i]);
			}
		}
		
		JFileChooser fc = new JFileChooser(new File("src/../"));
		fc.showOpenDialog(null);
		try {
			File f = fc.getSelectedFile();
			PrintWriter log = new PrintWriter("S2^48.txt");
			if (f.exists() && f.isFile()) {
				List<String> lines = Files.readAllLines(f.toPath());
				for(String line : lines) {
					String[] part = line.split(" ");
					long seed = Long.parseLong(part[0]);
					long base = seed;
					int x = Integer.parseInt(part[1]);
					int z = Integer.parseInt(part[2]);
					xzPair coords = new xzPair(x, z);
					for (int i= 0; i < 65536; seed += 281474976710656L, i++) {
						biomeGenerator generator = new biomeGenerator(seed, 1);
						strongholdFinder n = new strongholdFinder();
						xzPair shLoc = n.findStrongholds(seed, generator, biomeArrayList);
						if(shLoc!=null) {
							if (shLoc.getX()>>4==coords.getX()&&shLoc.getZ()>>4==coords.getZ()) {
								System.out.println(base+" "+seed+" "+shLoc.getX()+" "+shLoc.getZ());
								log.println(base+" "+seed+" "+shLoc.getX()+" "+shLoc.getZ());
							}
						}
					}
				}
				log.flush();
				log.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}