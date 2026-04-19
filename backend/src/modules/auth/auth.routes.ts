import { Router } from "express";
import { register, login, logout } from "./auth.controller";
import { protect } from "../../middleware/auth.middleware";

const router = Router();

router.post("/register", register);
router.post("/login", login);
router.post("/logout", protect, logout);

export default router;