package com.macaco.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import com.macaco.MacacoMod;

public class MacacoClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(MacacoMod.MACACO_VERMELHO, MacacoRenderer::new);
		ModelLayerRegistry.registerModelLayer(MacacoModel.LAYER, MacacoModel::createBodyLayer);
	}
}
