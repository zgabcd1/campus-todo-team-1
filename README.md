# CampusTodo

CampusTodo 是《软件工程》课程中用于练习 Git 与 GitHub 团队协同开发的 Java 项目。项目采用 Java 17、Maven 和 JUnit 5，可直接使用 IntelliJ IDEA 打开。

当前版本：支持新增任务和列出任务。

> 重要：上面这一行是合并冲突实验的固定锚点。只有实验任务明确要求时才修改，且不要提前合并其他同学的文字。

## 1. 初始能力

- `Task`：包含 `id`、`title` 和 `completed` 三个字段。
- `TaskService.addTask(String title)`：新增任务。
- `TaskService.listAll()`：按加入顺序返回任务快照。
- 基线测试：覆盖正常新增任务与空白标题校验。

## 2. 环境要求

- JDK 17
- IntelliJ IDEA 2023.3 或更高版本
- Maven 3.9 或 IntelliJ IDEA 内置 Maven
- Git 2.x

## 3. 打开与验证

1. 解压后，在 IntelliJ IDEA 中选择 **File → Open**，打开本目录中的 `pom.xml`。
2. 在 Project SDK 中选择 JDK 17，等待 Maven 依赖加载完成。
3. 打开 Maven 工具窗口，执行 `Lifecycle → test`；也可在终端执行：

```bash
mvn test
```

初始版本应有 2 个测试通过。

## 4. 团队迭代任务

| Issue | 负责人 | 目标 | 建议分支 |
| --- | --- | --- | --- |
| #1 Priority filter | 开发者 A | 增加 HIGH/MEDIUM/LOW，并支持按优先级筛选 | `feature/1-priority-filter` |
| #2 Complete task | 开发者 B | 按编号完成任务，并处理异常与重复完成 | `feature/2-complete-task` |
| #3 CI and guide | 质量负责人 Q | 增加 Maven CI、PR 模板并完善协作说明 | `feature/3-ci-guide` |

详细验收标准以教师发放的《实验任务书》为准。建议合并顺序为 `#3 → #1 → #2`，以便在 #2 中完成预定的 README 冲突练习。

## 5. Git 起步

本压缩包不包含 `.git` 目录。仓库管理员首次解压后执行：

```bash
git init
git add .
git commit -m "chore: initialize CampusTodo baseline"
git branch -M main
git remote add origin <REPOSITORY_URL>
git push -u origin main
git tag -a v0.1.0 -m "CampusTodo starter baseline"
git push origin v0.1.0
```

## 6. 协作约束

- 一个功能分支只解决一个 Issue，禁止直接向 `main` 提交功能代码。
- 提交信息采用 `<type>: <说明>`，例如 `test: specify task completion rules`。
- Pull Request 必须关联 Issue，并提供测试证据和自检结果。
- 作者不能批准自己的 Pull Request；评审意见处理完毕且 CI 通过后再合并。
- 不提交 `.idea/`、`target/`、访问令牌、账号密码或个人隐私数据。
- 禁止使用 `git push --force` 修改共享的 `main` 分支。

## 7. 协作流程（GitHub Flow）

本仓库按以下步骤完成一次迭代，任何修改都必须走完整闭环：

1. **领取 Issue**：在 Issues 中认领任务，确认验收标准与负责人。
2. **创建功能分支**：基于最新 `main` 创建 `feature/<issue号>-<主题>`：

   ```bash
   git switch main
   git pull --ff-only origin main
   git switch -c feature/1-priority-filter
   ```

3. **小步提交**：先写测试（红），再实现功能（绿），每次提交只做一件事，提交后推送：

   ```bash
   git add src/test && git commit -m "test: specify priority filtering behavior"
   git add src/main && git commit -m "feat: filter tasks by priority"
   git push -u origin feature/1-priority-filter
   ```

4. **创建 Pull Request**：Base 为 `main`，正文使用 `Closes #编号` 关联 Issue，按 PR 模板填写修改说明、测试证据与自检清单，并指定一名 Reviewer。
5. **CI 检查**：`.github/workflows/maven.yml` 会在 push 到 `main` 和针对 `main` 的 PR 上自动执行 `mvn -B verify`；CI 红时须在同一分支上补充提交直至通过。
6. **代码评审**：评审者至少提出一条可验证、可执行的意见；作者修改并推送新提交后，评审者 Approve。
7. **合并**：按 `#3 → #1 → #2` 顺序合并；合并前确认 CI 绿、评审意见全部解决。
8. **同步与清理**：合并后删除功能分支，本地执行 `git pull --ff-only origin main` 与 `git fetch --prune` 同步。
