import jwt from "jsonwebtoken";
import { env } from "../config/env";

export const generateToken = (payload: string) => {
    return jwt.sign({ payload }, env.jwtSecret, { expiresIn: env.jwtExpiresIn as jwt.SignOptions["expiresIn"] });
};