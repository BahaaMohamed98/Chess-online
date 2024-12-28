# Variables
$JAR_FILE = "Chess_online.jar"
$JAR_DIR = "../build/"
$ICON_FILE = "../assets/images/gameIcon.ico"
$MAIN_CLASS = "ui.ChessApp"
$APP_NAME = "Chess-online"
$VERSION = "0.0.1"
$DESCRIPTION = "A simple chess game with both local and online play support."
$COPYRIGHT = "© 2021 BahaaMohamed98"
$ABOUT_URL = "https://github.com/BahaaMohamed98/Chess-online"
$VENDOR = "BahaaMohamed98"
$OUTPUT_DIR = "../build/"

# Run jpackage
& jpackage `
    --name $APP_NAME `
    --input $JAR_DIR `
    --main-jar $JAR_FILE `
    --main-class $MAIN_CLASS `
    --dest $OUTPUT_DIR `
    --icon $ICON_FILE `
    --vendor $VENDOR `
    --description $DESCRIPTION `
    --app-version $VERSION `
    --copyright $COPYRIGHT `
    --about-url $ABOUT_URL `
    --verbosee `
    --type exe `
    --win-menu `
    --win-shortcut
