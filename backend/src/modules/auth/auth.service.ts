import bcrypt from "bcryptjs";
import { User } from "./user.model";
import { generateToken } from "../../utils/jwt";

export const registerUser = async (
    name: string,
    email: string,
    password: string
) => {
    const existingUser = await User.findOne({ email });

    if (existingUser) {
        throw new Error("Email already registered");
    }

    const hashedPassword = await bcrypt.hash(password, 10);

    const user = await User.create({
        name,
        email,
        password: hashedPassword
    });

    return {
        id: user._id,
        name: user.name,
        email: user.email
    };
};

export const loginUser = async (email: string, password: string) => {
    const user = await User.findOne({ email });
    if (!user) {
        throw new Error("Invalid credentials");
    }
    const isMatch = await bcrypt.compare(password, user.password);
    if (!isMatch) {
        throw new Error("Password Incorrect");
    }
    const token = generateToken(user._id.toString());
    return {
        token,
        user: {
            id: user._id,
            name: user.name,
            email: user.email,
        },
    };
}

export const logoutUser = async (userId: string) => {
    const user = await User.findByIdAndUpdate(userId, { isOnline: false, lastSeen: new Date() }, { new: true }).select("-password");
    if (!user) {
        throw new Error("User not found!");
    }
    return user;
}