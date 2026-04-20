import { Router } from "express";
import {
    startChat,
    getConversations,
    getMessages,
    sendMessage
} from "./chat.controller";
import { protect } from "../../middleware/auth.middleware";

const router = Router();

router.post(
    "/start/:userId",
    protect,
    startChat
);

router.get(
    "/conversations",
    protect,
    getConversations
);

router.get(
    "/messages/:conversationId",
    protect,
    getMessages
);

router.post(
    "/message/:conversationId",
    protect,
    sendMessage
);

export default router;