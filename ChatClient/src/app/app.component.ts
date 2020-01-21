import { Message } from './message';
import { Component } from '@angular/core';

@Component({
    selector: 'app-root',
    templateUrl: 'app.component.html'
})

export class AppComponent {

    title: string = 'WhatsApp Client';      // Ueberschrift
    wsUri: string = 'ws://localhost:8025/websockets/whatsapp';
    sendText: string = '';
    sendToGroup: string = 'schule';
    logBuffer: string = '';

    myUsername: string = 'anton';
    myPassword: string = 'passme';
    myGroups: Array<String> = ['schule','familie', 'sport'];  // Initialisierung nur zum Test

    inMessages: Array<Message> = [];
    showLogBuffer: Boolean = true;

    websocket: WebSocket;

    // Verbindungsaufbau
    doConnect() {
        this.websocket = new WebSocket(this.wsUri);
        //onOpen
        this.websocket.onopen = (evt) => this.logBuffer += 'Websocket connected\n';
        //onMessage  
        this.websocket.onmessage = (evt) => {
            this.logBuffer += evt.data + '\n';
            let message: Message = JSON.parse(evt.data);
            // data message recieved
            if (message.typ == 'data') {
                this.inMessages.push(message);
            }
            // groups message recieved
            else if (message.typ == 'groups') {
                this.myGroups = message.value;
            }

        }
        //onError
        this.websocket.onerror = (evt) => this.logBuffer += 'Error\n';
        //onClose
        this.websocket.onclose = (evt) => this.logBuffer += 'Websocket closed\n';
    }
    // Verbindung schließen
    doDisconnect(): void {
        this.websocket.close();
    }
    // Senden einer Message
    doSend(): void {
        let message: Message = new Message('data', this.myUsername, this.sendToGroup, this.sendText, []);
        this.websocket.send(JSON.stringify(message));
    }

    // Texte clearen
    doClear(): void {
        this.logBuffer = '';
    }

    // Login to Server
    doLogin() {
        this.websocket.send('{"typ":"login", "username":"' + this.myUsername + '", "password":"' + this.myPassword + '"}');
    }

    // Logout from Server
    doLogout() {
        this.websocket.send('{"typ":"logout", "username":"' + this.myUsername + '"}');
    }

    // Ask Server for my groups
    getGroups() {
        this.websocket.send('{"typ":"groups"}');
    }

    // derzeit nicht in Verwendung
    onGroupChange() {}

}
