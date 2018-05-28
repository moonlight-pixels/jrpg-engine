package com.moonlightpixels.jrpg.map;

public final class TiledProps {
    private TiledProps() { }

    public static final class Layer {
        private Layer() { }

        /**
         * <b>Layer Property name</b>: jrpg-map-layer.<br>
         * <b>Property Type</b>: Integer<br>
         * <br>
         * The Map layer this tiled layer belongs to.
         */
        public static final String MAP_LAYER = "jrpg-map-layer";

        /**
         * <b>Layer Type Property name</b>: jrpg-laye-typer.<br>
         * <b>Property Type</b>: From List<br>
         * <br>
         * The type of layer this layer is.
         */
        public static final String LAYER_TYPE = "jrpg-laye-typer";

        /**
         * <b>Layer Type Property value</b>: background.<br>
         */
        public static final String MAP_LAYER_VALUE_BACKGROUND = "background";

        /**
         * <b>Layer Type Property value</b>: foreground.<br>
         */
        public static final String MAP_LAYER_VALUE_FOREGROUND = "foreground";

        /**
         * <b>Layer Type Property value</b>: collision.<br>
         */
        public static final String MAP_LAYER_VALUE_COLLISION = "collision";
    }
}
