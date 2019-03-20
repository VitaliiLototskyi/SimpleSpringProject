#!/bin/sh
echo "Press 1 to sendMessages or press 2 to receiveMessages"

while :
    do
        read CHOICE
        echo "U entered: $CHOICE"
        case $CHOICE in
            1)
                java -jar SimpleSpringProject.jar
                break
                ;;
            2)
                java -cp SimpleSpringProject.jar ReceiveMessages
                break
                ;;
            *)
                echo "U entered wrong number. Try again"
                ;;
        esac
    done
echo "Script end"

