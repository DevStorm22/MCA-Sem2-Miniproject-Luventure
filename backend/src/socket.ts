import { Server } from "socket.io";

let io: Server;

export const initSocket = (server: any) => {
    io = new Server(server, {
        cors: {
            origin: "*",
            methods: ["GET", "POST"]
        }
    });

    io.on("connection", (socket) => {

        console.log("User connected:", socket.id);

        socket.on("join_room", (roomId: string) => {
            socket.join(roomId);
        });

        socket.on("disconnect", () => {
            console.log("Disconnected:", socket.id);
        });
    });

    return io;
};

export const getIO = () => io;