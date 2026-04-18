import dotenv from "dotenv";

dotenv.config();

export const env = {
    port: process.env.PORT || 5000,
    nodeEnv: process.env.NODE_ENV || "development",
    clientUrl: process.env.CLIENT_URL || "*",
    jwtSecret: process.env.JWT_SECRET || "",
    mongoUri: process.env.MONGO_URI || ""
};