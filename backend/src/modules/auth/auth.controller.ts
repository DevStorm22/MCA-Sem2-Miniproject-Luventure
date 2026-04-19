import { Request, Response } from "express";
import { registerUser, loginUser } from "./auth.service";

export const register = async (req: Request, res: Response) => {
    try {
        const { name, email, password } = req.body;

        if (!name || !email || !password) {
            return res.status(400).json({
                success: false,
                message: "All fields are required"
            });
        }

        const user = await registerUser(name, email, password);

        return res.status(201).json({
            success: true,
            message: "User registered successfully",
            data: user
        });
    } catch (error: any) {
        return res.status(400).json({
            success: false,
            message: error.message
        });
    }
};

export const login = async (req: Request, res: Response) => {
    try {
        const { email, password } = req.body;
        if (!email || !password) {
            throw new Error("Blank fileds");
        }
        if (!email.includes("@")) {
            throw new Error("Invalid Email");
        }
        const user = await loginUser(email, password);
        if (!user) {
            return res.status(400).json({
                success: false,
                message: "Invalid Credentials"
            })
        }
        return res.status(200).json({
            success: true,
            message: "Login Successfull",
            data: user
        })
    } catch (error: any) {
        return res.status(400).json({
            success: false,
            message: error.message
        })
    }
}

export const logout = async (req: any, res: any) => {
    try {
        req.user = null;
        return res.status(200).json({
            success: true,
            message: "Logout Successfully",
        });
    } catch (error: any) {
        return res.status(400).json({
            success: false,
            message: error.message
        })
    }
}