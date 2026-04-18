import express from "express";
import cors from "cors";
import helmet from "helmet";
import morgan from "morgan";
import { env } from "./config/env";

const app = express();

app.use(helmet());

app.use(cors({
    origin: env.clientUrl,
    credentials: true
}));

app.use(morgan("dev"));
app.use(express.json());

app.get("/health", (_req, res) => {
    res.status(200).json({
        success: true,
        message: "Luventure API is running"
    });
});

export default app;