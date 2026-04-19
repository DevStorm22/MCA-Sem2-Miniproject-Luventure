import { Router } from "express";
import { getMe } from "./user.controller";
import { protect } from "../../middleware/auth.middleware";

const router = Router();

router.get("/me", protect, getMe);

export default router;