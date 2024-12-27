# Variables
$JAR_FILE = "Chess.jar"
$ICON_FILE = "../assets/images/gameIcon.png"
$MAIN_CLASS = "ui.ChessApp"
$APP_NAME = "ChessGame"
$VERSION = "0.0.1"
$DESCRIPTION = "A simple chess game with both local and online play support."
$VENDOR = "BahaaMohamed98"
$OUTPUT_DIR = "../build/"

# Ensure the output directory exists
if (-not (Test-Path -Path $OUTPUT_DIR))
{
    New-Item -Path $OUTPUT_DIR -ItemType Directory
}

# Run jpackage
& jpackage `
    --name $APP_NAME `
    --input "../out/artifacts/Chess_jar/" `
    --main-jar $JAR_FILE `
    --main-class $MAIN_CLASS `
    --dest $OUTPUT_DIR `
    --icon $ICON_FILE `
    --vendor $VENDOR `
    --description $DESCRIPTION `
    --app-version $VERSION `
    --type exe `
    --win-menu `
    --win-shortcut `
    --win-per-user
