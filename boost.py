import os
import shutil

shutil.copy(".\\build\\libs\\FunctionalModernComputers-1.0-SNAPSHOT.jar", ".\\.minecraft\\mods")
os.system("cp ./build/libs/FunctionalModernComputers-1.0-SNAPSHOT.jar "
          "./cmcl/.minecraft/mods/FunctionalModernComputers-1.0-SNAPSHOT.jar")
os.system('java -jar ./cmcl/CMCL.jar -start SMDK')
