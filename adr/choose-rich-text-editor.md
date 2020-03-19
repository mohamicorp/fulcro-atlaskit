# Choose rich text editor

## Status
Proposed

## Context
Atlassian has a rich text editor which we like to use. Or of it is not possible to use that we want to use something 
that looks like their editor. 

### Tried candidates:

#### Atlaskit editor
The first thing that we tried to use is the atlaskit editor: `@atlaskit/editor-core` but this does not seem to work with
 `shadow-cljs`. And I had to add a ton of dependencies.
 
 When shadow tries to compile this with `yarn shadow-cljs watch app` we get:
 
 ```text
failed to publish!, offer! failed
{:msg {:type :build-log, :build-id :cards, :event {:type :flush-source, :resource-name "node_modules/@atlaskit/media-picker/dist/cjs/popup/middleware/searchGiphy.js", :timing :exit, :timing-id 2483, :depth 1, :stop 1584006642546, :duration 0, :shadow.build.log/level :info}}, :topic :shadow.cljs.model/build-log}
ExceptionInfo: failed to publish!, offer! failed
	shadow.cljs.devtools.server.system-bus/publish! (system_bus.clj:38)
	shadow.cljs.devtools.server.system-bus/publish! (system_bus.clj:33)
	shadow.cljs.devtools.server.worker.impl/build-configure/reify--15577 (impl.clj:169)
	shadow.cljs.util/log (util.clj:195)
	shadow.cljs.util/log (util.clj:189)
	shadow.build.output/flush-source (output.clj:250)
	shadow.build.output/flush-source (output.clj:229)
	shadow.build.output/flush-sources/fn--11332 (output.clj:262)
	shadow.build.async/queue-task/fn--11227 (async.clj:15)
	clojure.core/apply (core.clj:665)
	clojure.core/with-bindings* (core.clj:1973)
	clojure.core/with-bindings* (core.clj:1973)
	clojure.core/apply (core.clj:669)
	clojure.core/bound-fn*/fn--5749 (core.clj:2003)
	java.util.concurrent.FutureTask.run (FutureTask.java:266)
	java.util.concurrent.ThreadPoolExecutor.runWorker (ThreadPoolExecutor.java:1149)
	java.util.concurrent.ThreadPoolExecutor$Worker.run (ThreadPoolExecutor.java:624)
	java.lang.Thread.run (Thread.java:748)
```

If I run `yarn shadow-cljs watch app` multiple times it finally completes and then it fails in the browser.
```text
module$node_modules$$atlaskit$layer$dist$cjs$components$Layer.js:5 @atlaskit/layer has been deprecated. It is an internal component and should not be used directly.
shadow$provide.module$node_modules$$atlaskit$layer$dist$cjs$components$Layer @ module$node_modules$$atlaskit$layer$dist$cjs$components$Layer.js:5
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$layer$dist$cjs$index @ module$node_modules$$atlaskit$layer$dist$cjs$index.js:1
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$droplist$dist$cjs$components$Droplist @ module$node_modules$$atlaskit$droplist$dist$cjs$components$Droplist.js:3
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$droplist$dist$cjs$index @ module$node_modules$$atlaskit$droplist$dist$cjs$index.js:2
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$dropdown_menu$dist$cjs$components$DropdownMenuStateless @ module$node_modules$$atlaskit$dropdown_menu$dist$cjs$components$DropdownMenuStateless.js:2
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$dropdown_menu$dist$cjs$components$DropdownMenu @ module$node_modules$$atlaskit$dropdown_menu$dist$cjs$components$DropdownMenu.js:1
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$dropdown_menu$dist$cjs$index @ module$node_modules$$atlaskit$dropdown_menu$dist$cjs$index.js:1
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$avatar_group$dist$cjs$components$AvatarGroup @ module$node_modules$$atlaskit$avatar_group$dist$cjs$components$AvatarGroup.js:1
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$avatar_group$dist$cjs$index @ module$node_modules$$atlaskit$avatar_group$dist$cjs$index.js:1
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$media_ui$dist$cjs$BlockCard$ResolvedView$index @ module$node_modules$$atlaskit$media_ui$dist$cjs$BlockCard$ResolvedView$index.js:3
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$media_ui$dist$cjs$BlockCard$index @ module$node_modules$$atlaskit$media_ui$dist$cjs$BlockCard$index.js:1
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$media_ui$dist$cjs$index @ module$node_modules$$atlaskit$media_ui$dist$cjs$index.js:1
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$media_card$dist$cjs$utils$lightCards$styled @ module$node_modules$$atlaskit$media_card$dist$cjs$utils$lightCards$styled.js:1
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$media_card$dist$cjs$utils$lightCards$cardLoading @ module$node_modules$$atlaskit$media_card$dist$cjs$utils$lightCards$cardLoading.js:2
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$media_card$dist$cjs$index @ module$node_modules$$atlaskit$media_card$dist$cjs$index.js:1
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$editor_core$dist$cjs$plugins$media$nodeviews$media @ module$node_modules$$atlaskit$editor_core$dist$cjs$plugins$media$nodeviews$media.js:1
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$editor_core$dist$cjs$plugins$media$ui$Media$DropPlaceholder @ module$node_modules$$atlaskit$editor_core$dist$cjs$plugins$media$ui$Media$DropPlaceholder.js:2
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$editor_core$dist$cjs$plugins$media$pm_plugins$main @ module$node_modules$$atlaskit$editor_core$dist$cjs$plugins$media$pm_plugins$main.js:3
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$editor_core$dist$cjs$utils$action @ module$node_modules$$atlaskit$editor_core$dist$cjs$utils$action.js:1
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$editor_core$dist$cjs$utils$index @ module$node_modules$$atlaskit$editor_core$dist$cjs$utils$index.js:9
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$editor_core$dist$cjs$commands$index @ module$node_modules$$atlaskit$editor_core$dist$cjs$commands$index.js:5
shadow.js.jsRequire @ shadow.js.js:37
shadow$provide.module$node_modules$$atlaskit$editor_core$dist$cjs$index @ module$node_modules$$atlaskit$editor_core$dist$cjs$index.js:1
shadow.js.jsRequire @ shadow.js.js:37
shadow.js.require @ shadow.js.js:58
```
We discontinued this experiment because the source is also closed source.
 
#### Options
* Draft.js (React and from Facebook)
* Quill (Currently used on cloud) 
* ProseMirror (Used by atlassian editor)
* Slatejs (Inspired by Draft, Quill and ProseMirror and completely based on React)

## Decision

We have chosen for SlateJS because it is completely based on React and it does not
seem hard to add things like Mentions later on with normal React components.

## Consequences

We need to build our own editor with React, but it will be way more extensable later on
with normal React components.

