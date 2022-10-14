import shutil
import os

os.system('.\\gradlew build')

shutil.copy(".\\build\\libs\\FunctionalModernComputers-1.0-SNAPSHOT.jar", "D:\\HXY\\MDK\\.minecraft\\mods")
os.system('.\\runClient.cmd')
