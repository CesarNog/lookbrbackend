/// <reference types="webpack" />
import { loader as l } from 'webpack';
export declare function useTransformerBasedLoader(value: boolean): void;
export default function aotCleanLoader(this: l.LoaderContext & {
    _compilation: any;
}, source: string | null, sourceMap: string | null): any;
