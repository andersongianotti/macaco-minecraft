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
	private final ModelPart armRight;
	private final ModelPart armLeft;
	private final ModelPart legRight;
	private final ModelPart legLeft;

	public MacacoModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.tail = root.getChild("tail");
		this.armRight = root.getChild("arm_right");
		this.armLeft = root.getChild("arm_left");
		this.legRight = root.getChild("leg_right");
		this.legLeft = root.getChild("leg_left");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition root = mesh.getRoot();

		// tronco: 7x8x5, y 7..15
		root.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -4.0F, -2.5F, 7.0F, 8.0F, 5.0F),
				PartPose.offset(0.0F, 11.0F, 0.0F));

		// cabeca: 7x7x7 em cima do tronco, y 0.5..7.5
		root.addOrReplaceChild("head",
				CubeListBuilder.create().texOffs(0, 16).addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F),
				PartPose.offset(0.0F, 4.0F, 0.0F));

		// focinho: 4x3x3 na frente da cabeca
		root.addOrReplaceChild("snout",
				CubeListBuilder.create().texOffs(32, 0).addBox(-2.0F, -1.5F, -1.5F, 4.0F, 3.0F, 3.0F),
				PartPose.offset(0.0F, 5.0F, -3.5F));

		// orelhas
		root.addOrReplaceChild("ear_right",
				CubeListBuilder.create().texOffs(48, 0).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F),
				PartPose.offset(-3.5F, 4.0F, 0.0F));
		root.addOrReplaceChild("ear_left",
				CubeListBuilder.create().texOffs(48, 0).mirror().addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F),
				PartPose.offset(3.5F, 4.0F, 0.0F));

		// bracos: 2x7x2 pendurados nas laterais, y 7..14
		root.addOrReplaceChild("arm_right",
				CubeListBuilder.create().texOffs(0, 32).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F),
				PartPose.offset(-4.5F, 8.0F, 0.0F));
		root.addOrReplaceChild("arm_left",
				CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F),
				PartPose.offset(4.5F, 8.0F, 0.0F));

		// pernas: 3x6x3, y 18..24 (chao)
		root.addOrReplaceChild("leg_right",
				CubeListBuilder.create().texOffs(16, 32).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F),
				PartPose.offset(-1.5F, 18.0F, 0.0F));
		root.addOrReplaceChild("leg_left",
				CubeListBuilder.create().texOffs(16, 32).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F),
				PartPose.offset(1.5F, 18.0F, 0.0F));

		// rabo: 2x9x2 pendurado atras
		root.addOrReplaceChild("tail",
				CubeListBuilder.create().texOffs(32, 8).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 9.0F, 2.0F),
				PartPose.offset(0.0F, 15.0F, 2.0F));

		return LayerDefinition.create(mesh, 64, 64);
	}

	@Override
	public void setupAnim(MacacoRenderState state) {
		float swing = state.walkAnimationPos * 0.6662F;
		float amount = Math.min(state.walkAnimationSpeed, 1.0F);

		this.legRight.xRot = Mth.cos(swing) * 1.0F * amount;
		this.legLeft.xRot = Mth.cos(swing + (float) Math.PI) * 1.0F * amount;
		this.armRight.xRot = Mth.cos(swing + (float) Math.PI) * 0.6F * amount;
		this.armLeft.xRot = Mth.cos(swing) * 0.6F * amount;

		this.head.xRot = state.xRot * Mth.DEG_TO_RAD * 0.5F;
		this.head.yRot = state.yRot * Mth.DEG_TO_RAD * 0.5F;

		this.tail.yRot = Mth.sin(state.ageInTicks * 0.1F) * 0.4F;
		this.tail.zRot = Mth.sin(state.ageInTicks * 0.07F) * 0.2F;
	}
}
