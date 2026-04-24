import { Router } from "express";
import {
    getMe,
    updateMe,
    discoverUsers
} from "./user.controller";

import { protect } from "../../middleware/auth.middleware";

const router = Router();

router.get("/me", protect, getMe);
router.patch("/update-profile", protect, updateMe);
router.get("/discover", protect, discoverUsers);

export default router;