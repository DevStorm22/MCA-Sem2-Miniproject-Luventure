import express from "express";
import cors from "cors";
import helmet from "helmet";
import morgan from "morgan";
import { env } from "./config/env";
import authRoutes from "./modules/auth/auth.routes";
import { protect } from "./middleware/auth.middleware";

const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(helmet());

app.use(cors({
    origin: env.clientUrl,
    credentials: true
}));

app.use(morgan("dev"));

app.get("/health", (_req, res) => {
    res.status(200).json({
        success: true,
        message: "Luventure API is running"
    });
});

app.use("/api/auth", authRoutes);

app.get("/api/test/private", protect, (req, res) => {
    res.json({
        success: true,
        message: "Private route accessed successfully",
    })
})

export default app;