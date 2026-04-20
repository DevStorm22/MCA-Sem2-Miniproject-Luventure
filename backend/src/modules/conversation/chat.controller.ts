import { Request, Response } from "express";
import { Conversation } from "./conversation.model";
import { Message } from "./message.model";
import { authRequest } from "../../middleware/auth.middleware";

export const startChat = async (
    req: authRequest,
    res: Response
) => {
    try {
        const myId = req.user!.userId as string;
        const otherId = req.params.userId as string;

        let convo = await Conversation.findOne({
            participants: { $all: [myId, otherId] }
        });

        if (!convo) {
            convo = await Conversation.create({
                participants: [myId, otherId]
            });
        }

        return res.status(200).json({
            success: true,
            data: convo
        });

    } catch {
        return res.status(500).json({
            success: false,
            message: "Failed to start chat"
        });
    }
};

export const getConversations = async (
    req: authRequest,
    res: Response
) => {
    try {
        const myId = req.user?.userId;

        const list = await Conversation.find({
            participants: myId
        })
            .sort({ lastMessageAt: -1 })
            .populate("participants", "name email");

        return res.json({
            success: true,
            data: list
        });

    } catch {
        return res.status(500).json({
            success: false,
            message: "Failed to fetch chats"
        });
    }
};

export const getMessages = async (
    req: Request,
    res: Response
) => {
    try {
        const messages = await Message.find({
            conversationId:
                req.params.conversationId
        }).sort({ createdAt: 1 });

        return res.json({
            success: true,
            data: messages
        });

    } catch {
        return res.status(500).json({
            success: false,
            message: "Failed to fetch messages"
        });
    }
};

export const sendMessage = async (
    req: authRequest,
    res: Response
) => {
    try {
        const sender = req.user!.userId as string;

        const { text } = req.body;

        const msg = await Message.create({
            conversationId: req.params.conversationId as string,
            sender: sender as string,
            text
        });

        await Conversation.findByIdAndUpdate(
            req.params.conversationId as string,
            {
                lastMessage: text,
                lastMessageAt: new Date()
            }
        );

        return res.status(201).json({
            success: true,
            data: msg
        });

    } catch {
        return res.status(500).json({
            success: false,
            message: "Failed to send message"
        });
    }
};