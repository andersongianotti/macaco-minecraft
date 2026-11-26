package com.macaco.client;

import com.macaco.MacacoMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class MacacoModel extends EntityModel<MacacoRenderState> {

	public static final ModelLayerLocation LAYER =
			new ModelLayerLocation(MacacoMod.id("macaco_vermelho"), "main");

	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart tail;
	private final ModelPart legFrontRight;
	private final ModelPart legFrontLeft;
	private final ModelPart legBackRight;
	private final ModelPart legBackLeft;

	public MacacoModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.tail = root.getChild("tail");
		this.legFrontRight = root.getChild("leg_front_right");
		this.legFrontLeft = root.getChild("leg_front_left");
		this.legBackRight = root.getChild("leg_back_right");
		this.legBackLeft = root.getChild("leg_back_left");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		// body: 8x7x16, ocupa y 9..16 (chao = y24)
		root.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 7.0F, 16.0F),
				PartPose.offset(0.0F, 13.0F, 0.0F));

		// cabeca: 7x7x7 na frente (z negativo), y 2..9
		root.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 24).addBox(-3.5F, -3.5F, -4.0F, 7.0F, 7.0F, 7.0F),
				PartPose.offset(0.0F, 5.5F, -8.0F));

		// focinho: 4x3x3 saindo da frente da cabeca
		root.addOrReplaceChild("snout",
				CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, -1.5F, -2.5F, 4.0F, 3.0F, 3.0F),
				PartPose.offset(0.0F, 6.0F, -11.5F));

		// orelhas
		root.addOrReplaceChild("ear_right",
				CubeListBuilder.create().texOffs(48, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F),
				PartPose.offset(-3.5F, 2.5F, -8.0F));
		root.addOrReplaceChild("ear_left",
				CubeListBuilder.create().texOffs(48, 0).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F),
				PartPose.offset(3.5F, 2.5F, -8.0F));

		// patas: 3x8x3, de y16 ate y24 (chao)
		root.addOrReplaceChild("leg_front_right",
				CubeListBuilder.create().texOffs(0, 40).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F),
				PartPose.offset(-3.0F, 16.0F, -5.0F));
		root.addOrReplaceChild("leg_front_left",
				CubeListBuilder.create().texOffs(0, 40).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F),
				PartPose.offset(3.0F, 16.0F, -5.0F));
		root.addOrReplaceChild("leg_back_right",
				CubeListBuilder.create().texOffs(0, 40).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F),
				PartPose.offset(-3.0F, 16.0F, 5.0F));
		root.addOrReplaceChild("leg_back_left",
				CubeListBuilder.create().texOffs(0, 40).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 8.0F, 3.0F),
				PartPose.offset(3.0F, 16.0F, 5.0F));

		// rabo: 2x10x2 pendurado atras
		root.addOrReplaceChild("tail",
				CubeListBuilder.create().texOffs(0, 52).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 10.0F, 2.0F),
				PartPose.offset(0.0F, 14.0F, 7.0F));

		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(MacacoRenderState state) {
		float swing = state.walkAnimationPos * 0.6662F;
		float amount = Math.min(state.walkAnimationSpeed, 1.0F);

		this.legFrontRight.xRot = Mth.cos(swing) * 1.2F * amount;
		this.legBackLeft.xRot = Mth.cos(swing) * 1.2F * amount;
		this.legFrontLeft.xRot = Mth.cos(swing + (float) Math.PI) * 1.2F * amount;
		this.legBackRight.xRot = Mth.cos(swing + (float) Math.PI) * 1.2F * amount;

		this.head.xRot = state.xRot * Mth.DEG_TO_RAD * 0.5F;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD * 0.5F;

		this.tail.yRot = Mth.sin(state.ageInTicks * 0.1F) * 0.4F;
		this.tail.zRot = Mth.sin(state.ageInTicks * 0.07F) * 0.2F;
	}
}
