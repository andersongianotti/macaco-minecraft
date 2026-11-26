package com.macaco.client;

import com.macaco.MacacoMod;
import com.macaco.entity.MacacoVermelhoEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class MacacoRenderer extends MobRenderer<MacacoVermelhoEntity, MacacoRenderState, MacacoModel> {

	private static final Identifier TEXTURE = MacacoMod.id("textures/entity/macaco_vermelho.png");

	public MacacoRenderer(EntityRendererProvider.Context context) {
		super(context, new MacacoModel(context.bakeLayer(MacacoModel.LAYER)), 0.4F);
	}

	@Override
	public Identifier getTextureLocation(MacacoRenderState state) {
		return TEXTURE;
	}

	@Override
	public MacacoRenderState createRenderState() {
		return new MacacoRenderState();
	}
}
