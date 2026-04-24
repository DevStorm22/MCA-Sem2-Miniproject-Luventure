import { Response } from "express";
import { getCurrentUser, updateProfile } from "./user.service";
import { authRequest } from "../../middleware/auth.middleware";
import { User } from "../auth/user.model";

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

export const discoverUsers = async (req: any, res: any) => {
    try {
        const userId = req.user?.userId;

        const users = await User.find({
            _id: { $ne: userId }
        }).select("_id name email");

        return res.status(200).json({
            success: true,
            message: "Users fetched successfully",
            data: users
        });

    } catch (error: any) {
        return res.status(400).json({
            success: false,
            message: error.message
        });
    }
};