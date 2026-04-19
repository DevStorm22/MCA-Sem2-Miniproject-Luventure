import { Request, Response } from "express";
import { getCurrentUser } from "./user.service";
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