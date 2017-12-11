/// <reference types="webpack" />
import { loader as l } from 'webpack';
export interface AotCleanupLoaderOptions {
    /**
     * If false the plugin is a ghost, it will not perform any action.
     * This property can be used to trigger AOT on/off depending on your build target (prod, staging etc...)
     *
     * The state can not change after initializing the plugin.
     * @default true
     */
    disable?: false;
    /**
     * A path to a TSConfig file, optional if a plugin is supplied.
     * When both are available `tsConfigPath` wins.
     */
    tsConfigPath?: any;
    /**
     * Optional TS compiler options.
     *
     * > Some options set by the loader can not change.
     */
    compilerOptions?: any;
}
/**
 * Reset the loader, allows running a new program on the same session.
 * @internal
 */
export declare function resetLoader(): void;
export declare function aotCleanLoader(this: l.LoaderContext & {
    _compilation: any;
}, source: string | null, sourceMap: string | null): void;
