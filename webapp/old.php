<!DOCTYPE html>
<html lang="de">
<head>
    <meta charset="UTF-8">
    <title>Notfallchecklisten</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        ul { list-style-type: none; }
        li { padding: 10px; border: 1px solid #ccc; margin: 5px 0; }
        .checklist-item { background-color: #f5f5f5; cursor: pointer; }
        
        .checked {
            background-color: green;
            color: white;
        }
        .checklist-item {
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
// Datenbankaufruf
$servername = "fdb1028.awardspace.net";
$username = "4354180_checklisten";
$password = "ANNA8675";
$dbname = "4354180_checklisten";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT Reihenfolge, Checkpoint FROM RSI_und_Intubation";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    echo "<ul>";
    while($row = $result->fetch_assoc()) {
        echo "<li class='checklist-item'>" . $row["Checkpoint"] . "</li>";
    }
    echo "</ul>";
} else {
    echo "Keine Einträge in der Checkliste gefunden.";
}

$conn->close();
?>
</body>
</html>