import http from "http";
import app from "./app";
import { connectDB } from "./config/db";
import { env } from "./config/env";
import { initSocket } from "./socket";

const server = http.createServer(app);

initSocket(server);

server.listen(env.port, async () => {
    await connectDB();

    console.log(`Server running on port ${env.port}`);
});