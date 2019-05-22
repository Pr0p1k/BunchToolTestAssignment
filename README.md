# BunchTool
Tool that recursively renames _\*.kt_ and _\*.java_ files
in the given directories appending _.2019_ to them

# Build 
Run *gradle jar* to generate **build/libs/bunchTool.jar**

# Run 
Run *java -jar build/libs/bunchTool.kt \<args\>* where args are arbitrary amount of directories
Works on Windows and \*NIX

# Restrictions
Paths shouldn't contain "/" in windows and "\\" in linux
Console should support **ANSI escape sequences** for colored output. In Windows both cmd and PowerShell don't support it, so you can use ubuntu subsystem (win10) or git bash etc.
