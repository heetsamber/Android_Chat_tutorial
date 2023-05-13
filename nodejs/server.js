

const express = require('express');

const app = express();
const socketio = require('socket.io');
const port = 8000

// express 내장 bodyparser 사용  [express 4.16.0]
app.use(express.json());
app.use(express.urlencoded({extended:true}));


const server = app.listen(port, ()=> {
    console.log(`connect server : ${port}`);
});


// socketio 서버로 변환

const io = socketio.listen(server)

io.on('connection',function(socket) {

    console.log(`Connection : SocketId = ${socket.id}`);
    
    let userName = '';
    
    socket.on('subscribe', function(data) {
        console.log('subscribe trigged')
        const room_data = JSON.parse(data)
        userName = room_data.userName;
        const roomName = room_data.roomName;
    
        socket.join(`${roomName}`)
        console.log(`Username : ${userName} joined Room Name : ${roomName}`)
        
        io.to(`${roomName}`).emit('newUserToChatRoom',userName);

    });

    socket.on('unsubscribe',function(data) {
        console.log('unsubscribe trigged')
        const room_data = JSON.parse(data)
        const userName = room_data.userName;
        const roomName = room_data.roomName;
    
        console.log(`Username : ${userName} leaved Room Name : ${roomName}`)
        socket.broadcast.to(`${roomName}`).emit('userLeftChatRoom',userName)
        socket.leave(`${roomName}`)
    });

    socket.on('newMessage',function(data) {
        console.log('newMessage triggered')

        const messageData = JSON.parse(data)
        const messageContent = messageData.messageContent
        const roomName = messageData.roomName

        console.log(`[Room Number ${roomName}] ${userName} : ${messageContent}`)
        // Just pass the data that has been passed from the writer socket

        const chatData = {
            userName : userName,
            messageContent : messageContent,
            roomName : roomName
        }
        socket.broadcast.to(`${roomName}`).emit('updateChat',JSON.stringify(chatData)) // Need to be parsed into Kotlin object in Kotlin
    });

    socket.on('disconnect', function () {
        console.log("One of sockets disconnected from our server.")
    });
})

module.exports = server; //Exporting for test
