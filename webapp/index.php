<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Notfallchecklisten</title>
    <style>
        body { line-height: 1.5; letter-spacing: 0px; font-size: irem; font-family: Arial, sans-serif; padding: 15px; }
        a { text-decoration: none; color: black; padding: 10px; }
        h1 { margin-bottom: 40px }
        h2 { margin-bottom: 40px }
        a:visited { color: black; }
        p { line-height: 1.5; margin-top: 20px; margin-bottom: 20px; }
        ul { list-style-type: none; }
        li { 
            background-color: #f5f5f5;
            padding-top: 10px;
            padding-bottom: 10px;
            border: 3px solid #ccc; 
            margin: 20px 40px 20px -20px; 
        }
        
        .checked {
            background-color: green;
            color: white;
        }
        .checklist-item {
            background-color: #f5f5f5;
            cursor: pointer;
            padding: 10px;
            margin: 20px 40px 20px -20px; 
            position: relative;
            overflow: hidden;
            z-index: 1;
        }
        .swipe-overlay {
            position: absolute;
            top: 0;
            left: 0;
            height: 100%;
            width: 0;
            background-color: green;
            z-index: -1;
            transition: width 0.3s;
        }
        #back-button {
            margin-top: 40px;
            margin-bottom: 40px
            padding: 20px;
            background-color: #007bff;
            color: white;
            border-radius: 5px;
            text-align: center;
        }
        
        #instruction {
            font-weight: bold;
            margin-bottom: 40px;
            margin-top: 40px;
        }
    </style>
    <script src="hammer.min.js"></script>
    <script>
        document.addEventListener("DOMContentLoaded", function() {
            let checklistItems = document.querySelectorAll('.checklist-item');
            checklistItems.forEach(function(item) {
                let hammerInstance = new Hammer(item);
                let swipeOverlay = document.createElement('div');
                swipeOverlay.className = 'swipe-overlay';
                item.appendChild(swipeOverlay);

                hammerInstance.on('panmove', function(e) {
                    let progress = Math.min(Math.max(e.deltaX, 0), item.offsetWidth);
                    swipeOverlay.style.width = progress + 'px';
                });

                hammerInstance.on('swiperight', function() {
                    swipeOverlay.style.width = '100%';
                    item.classList.add('checked');
                });

                hammerInstance.on('swipeleft', function() {
                    swipeOverlay.style.width = '0%';
                    item.classList.remove('checked');
                });
            });
        });
    </script>
</head>
<body>

<h1>Notfallchecklisten</h1>
        
<?php
$config = require 'data/config.php';
$servername = $config['servername'];
$username = $config['username'];
$password = $config['password'];
$dbname = $config['dbname'];
        
$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if (isset($_GET['liste'])) {
    $checklist = $_GET['liste'];
    $sql = "SELECT Reihenfolge, Checkpoint FROM $checklist";
    $result = $conn->query($sql);
    $displayTitle = str_replace("_", " ", $checklist); // Unterstriche durch Leerzeichen ersetzen

    echo "<h2 id='$displayTitle'>$displayTitle</h2>";
    echo "<ul>";

    if ($result->num_rows > 0) {
        while ($row = $result->fetch_assoc()) {
            echo "<li class='checklist-item'>" . $row["Checkpoint"] . "</li>";
        }
    } else {
        echo "<li>Keine Einträge in der Checkliste gefunden.</li>";
    }

    echo "</ul>";
    echo "<p id='instruction'>Checkpoints durch Swipe nach Rechts bestätigen, durch Swipe nach links Bestätigung zurücknehmen</p>";
    echo "<a href='index.php' id='back-button'>Zurück zur Übersicht</a>";

} else {
    $sql = "SHOW TABLES";
    $result = $conn->query($sql);

    echo "<ul>";
	while ($row = $result->fetch_assoc()) {
    	$table = $row["Tables_in_" . $dbname];
    	$displayName = str_replace("_", " ", $table); // Unterstriche durch Leerzeichen ersetzen
    	echo "<li><a href='?liste=$table'>$displayName</a></li>";
	}
	echo "</ul>";
}

$conn->close();
?>

</body>
</html>