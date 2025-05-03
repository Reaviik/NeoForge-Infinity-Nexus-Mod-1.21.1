package com.Infinity.Nexus.Mod.entity.client;

import com.Infinity.Nexus.Mod.entity.animations.ModAnimmationDefinitions;
import com.Infinity.Nexus.Mod.entity.custom.Asgreon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AsgreonModel<T extends Asgreon> extends HierarchicalModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "asgreon-vanilla"), "main");
	private final ModelPart asgreon;
	private final ModelPart head;
	private final ModelPart body;

	public AsgreonModel(ModelPart root) {
		this.asgreon = root.getChild("asgreon");
		this.head = asgreon.getChild("body").getChild("torso").getChild("head");
		this.body = asgreon.getChild("body");
	}
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition rhino = partdefinition.addOrReplaceChild("asgreon", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = rhino.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(13, 52).addBox(-3.0F, -1.5F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.5F, 0.5F));

		PartDefinition head = torso.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -6.5F, -0.5F));

		PartDefinition skull = head.addOrReplaceChild("skull", CubeListBuilder.create().texOffs(72, 31).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_ear = skull.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.0F, 0.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, -3.0F, -1.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, -3.0F, -3.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-2.0F, -3.0F, 2.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -3.0F, -0.5F));

		PartDefinition right_ear = skull.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.0F, -3.0F, 0.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).mirror().addBox(-2.0F, -3.0F, -1.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).mirror().addBox(-2.0F, -3.0F, -3.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 0).mirror().addBox(-2.0F, -3.0F, 2.5F, 4.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-4.0F, -3.0F, -0.5F));

		PartDefinition left_eye = skull.addOrReplaceChild("left_eye", CubeListBuilder.create(), PartPose.offsetAndRotation(5.8F, 10.1F, -32.9F, 0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r1 = left_eye.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(57, 46).addBox(5.2052F, -2.6833F, 2.198F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.8F, -0.2748F, 34.1342F, -1.309F, 1.309F, -1.5708F));

		PartDefinition light2 = left_eye.addOrReplaceChild("light2", CubeListBuilder.create(), PartPose.offset(-3.2552F, -3.4192F, 28.9667F));

		PartDefinition cube_r2 = light2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(55, 46).addBox(5.3052F, -1.4833F, 2.398F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5448F, 3.1445F, 5.1675F, -1.309F, 1.309F, -1.5708F));

		PartDefinition left_eyelid = skull.addOrReplaceChild("left_eyelid", CubeListBuilder.create(), PartPose.offset(5.05F, 10.1F, -32.9F));

		PartDefinition cube_r3 = left_eyelid.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(42, 85).addBox(4.4552F, -4.0F, 1.3052F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.05F, -9.1F, 32.9F, 0.0F, 1.5708F, 0.0F));

		PartDefinition right_eyelid = skull.addOrReplaceChild("right_eyelid", CubeListBuilder.create(), PartPose.offset(-5.05F, 10.1F, -32.9F));

		PartDefinition cube_r4 = right_eyelid.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(42, 85).mirror().addBox(-5.4552F, -4.0F, 1.3052F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.05F, -9.1F, 32.9F, 0.0F, -1.5708F, 0.0F));

		PartDefinition right_eye = skull.addOrReplaceChild("right_eye", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.8F, 10.1F, -32.9F, 0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r5 = right_eye.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(57, 46).mirror().addBox(-6.2052F, -2.6833F, 2.198F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.8F, -0.2748F, 34.1342F, -1.309F, -1.309F, 1.5708F));

		PartDefinition light = right_eye.addOrReplaceChild("light", CubeListBuilder.create(), PartPose.offset(3.2552F, -3.4192F, 28.9667F));

		PartDefinition cube_r6 = light.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(55, 46).mirror().addBox(-6.3052F, -1.4833F, 2.398F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.5448F, 3.1445F, 5.1675F, -1.309F, -1.309F, 1.5708F));

		PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -15.5F, 24.0F, -1.309F, 0.0F, 0.0F));

		PartDefinition left_back_leg = body.addOrReplaceChild("left_back_leg", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition hexadecagon_r1 = left_back_leg.addOrReplaceChild("hexadecagon_r1", CubeListBuilder.create().texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r2 = left_back_leg.addOrReplaceChild("hexadecagon_r2", CubeListBuilder.create().texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5109F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r3 = left_back_leg.addOrReplaceChild("hexadecagon_r3", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r4 = left_back_leg.addOrReplaceChild("hexadecagon_r4", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r5 = left_back_leg.addOrReplaceChild("hexadecagon_r5", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5109F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.7489F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r6 = left_back_leg.addOrReplaceChild("hexadecagon_r6", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r7 = left_back_leg.addOrReplaceChild("hexadecagon_r7", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.7489F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r8 = left_back_leg.addOrReplaceChild("hexadecagon_r8", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r9 = left_back_leg.addOrReplaceChild("hexadecagon_r9", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition left_back_knee = left_back_leg.addOrReplaceChild("left_back_knee", CubeListBuilder.create(), PartPose.offset(5.5F, 4.0F, 34.0F));

		PartDefinition left_back_heel = left_back_knee.addOrReplaceChild("left_back_heel", CubeListBuilder.create(), PartPose.offset(0.0F, 10.0F, -0.5F));

		PartDefinition right_back_leg = body.addOrReplaceChild("right_back_leg", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition hexadecagon_r10 = right_back_leg.addOrReplaceChild("hexadecagon_r10", CubeListBuilder.create().texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r11 = right_back_leg.addOrReplaceChild("hexadecagon_r11", CubeListBuilder.create().texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5109F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r12 = right_back_leg.addOrReplaceChild("hexadecagon_r12", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r13 = right_back_leg.addOrReplaceChild("hexadecagon_r13", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r14 = right_back_leg.addOrReplaceChild("hexadecagon_r14", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5109F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.7489F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r15 = right_back_leg.addOrReplaceChild("hexadecagon_r15", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r16 = right_back_leg.addOrReplaceChild("hexadecagon_r16", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.7489F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r17 = right_back_leg.addOrReplaceChild("hexadecagon_r17", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r18 = right_back_leg.addOrReplaceChild("hexadecagon_r18", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition right_back_knee = right_back_leg.addOrReplaceChild("right_back_knee", CubeListBuilder.create(), PartPose.offset(-1.5104F, -2.0104F, -3.0F));

		PartDefinition right_front_leg = body.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition hexadecagon_r19 = right_front_leg.addOrReplaceChild("hexadecagon_r19", CubeListBuilder.create().texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r20 = right_front_leg.addOrReplaceChild("hexadecagon_r20", CubeListBuilder.create().texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5109F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r21 = right_front_leg.addOrReplaceChild("hexadecagon_r21", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r22 = right_front_leg.addOrReplaceChild("hexadecagon_r22", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r23 = right_front_leg.addOrReplaceChild("hexadecagon_r23", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5109F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.7489F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r24 = right_front_leg.addOrReplaceChild("hexadecagon_r24", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r25 = right_front_leg.addOrReplaceChild("hexadecagon_r25", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.7489F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r26 = right_front_leg.addOrReplaceChild("hexadecagon_r26", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r27 = right_front_leg.addOrReplaceChild("hexadecagon_r27", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition right_front_knee = right_front_leg.addOrReplaceChild("right_front_knee", CubeListBuilder.create(), PartPose.offset(-8.5F, 14.0F, -23.5F));

		PartDefinition left_front_leg = body.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition hexadecagon_r28 = left_front_leg.addOrReplaceChild("hexadecagon_r28", CubeListBuilder.create().texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r29 = left_front_leg.addOrReplaceChild("hexadecagon_r29", CubeListBuilder.create().texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5109F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r30 = left_front_leg.addOrReplaceChild("hexadecagon_r30", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r31 = left_front_leg.addOrReplaceChild("hexadecagon_r31", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r32 = left_front_leg.addOrReplaceChild("hexadecagon_r32", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5109F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.7489F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r33 = left_front_leg.addOrReplaceChild("hexadecagon_r33", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r34 = left_front_leg.addOrReplaceChild("hexadecagon_r34", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(25, 2).addBox(-0.5F, -10.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.7489F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r35 = left_front_leg.addOrReplaceChild("hexadecagon_r35", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.3562F, 0.0F, 0.0F));

		PartDefinition hexadecagon_r36 = left_front_leg.addOrReplaceChild("hexadecagon_r36", CubeListBuilder.create().texOffs(42, 19).addBox(-0.5F, -0.5F, -10.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition left_front_knee = left_front_leg.addOrReplaceChild("left_front_knee", CubeListBuilder.create(), PartPose.offset(5.5F, 14.0F, -23.5F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Asgreon entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		if(!entity.isSitting()) {
			this.applyHeadRotation(entity, netHeadYaw, headPitch, ageInTicks);
		}
		if(entity.isWalking()) {
			System.out.println("WALK");
			this.animateWalk(ModAnimmationDefinitions.ASGREON_WALK, limbSwing, limbSwingAmount, 1.2f, 2f);
		}

		this.animate(entity.idleAnimationState, ModAnimmationDefinitions.ASGREON_IDLE, ageInTicks, 1f);
		this.animate(entity.attackAnimationState, ModAnimmationDefinitions.ASGREON_ATTACK, ageInTicks, 2f);
		this.animate(entity.sitAnimationState, ModAnimmationDefinitions.ASGREON_SIT, ageInTicks, 1f);

	}

	private void applyHeadRotation(Asgreon entity, float pNetHeadYaw, float pHeadPitch, float pAgeInTicks){
		//pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0f, 30.0f);
		pHeadPitch = Mth.clamp(pHeadPitch, -45f, 45f);

		//this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
		this.body.xRot = pHeadPitch * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		asgreon.render(poseStack, buffer, packedLight, packedOverlay, color);
	}


	@Override
	public ModelPart root() {
		return asgreon;
	}
}