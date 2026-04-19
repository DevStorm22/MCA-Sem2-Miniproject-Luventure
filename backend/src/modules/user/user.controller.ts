import { Request, Response } from "express";
import { getCurrentUser, updateProfile } from "./user.service";
import { authRequest } from "../../middleware/auth.middleware";

export const getMe = async (req: authRequest, res: Response) => {
    try {
        const userId = req.user?.userId;
        if (!userId) {
            throw new Error("User Not Found");
        }
        const user = await getCurrentUser(userId!);
        return res.status(200).json({
            success: true,
            message: "User fetched successfully",
            data: user
        })
    } catch (error: any) {
        return res.status(400).json({
            success: false,
            message: error.message
        })
    }
}

export const updateMe = async (req: any, res: any) => {
    try {
        const userId = req.user?.userId;
        if (!userId) {
            throw new Error("User Not Found");
        }
        const user = await updateProfile(userId, req.body);
        return res.status(200).json({
            success: true,
            message: "Profile Updated Successfully",
            data: user,
        });
    } catch (error: any) {
        return res.status(400).json({
            success: false,
            message: error.message
        })
    }
}