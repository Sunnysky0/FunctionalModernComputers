import os
import shutil

print("Copying mod archive")
shutil.copy(".\\build\\libs\\FunctionalModernComputers-1.0-SNAPSHOT.jar", ".\\.minecraft\\mods")

print("Starting Minecraft via CMCL")
os.system('java -jar ./cmcl/CMCL.jar -start SMDK')

print("Minecraft terminated")
