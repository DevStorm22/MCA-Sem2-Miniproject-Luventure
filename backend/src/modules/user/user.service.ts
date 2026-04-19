import { User } from "../auth/user.model"

export const getCurrentUser = async (userId: string) => {
    const user = await User.findById(userId).select("-password");
    if (!user) {
        throw new Error("User Not Found");
    }
    return user;
}