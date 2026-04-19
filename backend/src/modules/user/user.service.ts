import { User } from "../auth/user.model"

export const getCurrentUser = async (userId: string) => {
    const user = await User.findById(userId).select("-password");
    if (!user) {
        throw new Error("User Not Found");
    }
    return user;
}

export const updateProfile = async (userId: string, updates: any) => {
    const allowedFields = ["name", "avtar", "bio", "interest"];
    const safeUpdates: any = {};
    for (const key of allowedFields) {
        if (updates[key] !== undefined) {
            safeUpdates[key] = updates[key];
        }
    }
    const user = await User.findByIdAndUpdate(userId, safeUpdates, { new: true }).select("-password");
    if (!user) {
        throw new Error("User not found!");
    }
    return user;
}