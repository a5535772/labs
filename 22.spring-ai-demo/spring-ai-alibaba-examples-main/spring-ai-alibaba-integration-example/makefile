JAVA_PROJECT_DIR=./backend
VITE_PROJECT_DIR=./frontend
IMAGE_NAME=spring-ai-alibaba-playground
DOCKERFILE_PATH=./docker/app/Dockerfile
TAG=$(shell git rev-parse --short HEAD)

.DEFAULT_GOAL := all

.PHONY: backend
backend:
	@echo "Building Java project with Maven..."
	@cd $(JAVA_PROJECT_DIR) && mvn clean package -DskipTests

.PHONY: frontend
frontend:
	@echo "Building Vite project with pnpm..."
	rm -rf $(VITE_PROJECT_DIR)/node_modules
	@cd $(VITE_PROJECT_DIR) && pnpm install && pnpm build

.PHONY: all
all: backend frontend
	@echo "All projects have been built."

.PHONY: clean
clean:
	@echo "Cleaning Java project..."
	@cd $(JAVA_PROJECT_DIR) && mvn clean
	@echo "Cleaning Vite project..."
	@cd $(VITE_PROJECT_DIR) && rm -rf dist

.PHONY: image
image:
	@echo "Building Docker image..."

	rm -rf docker/app/app.jar
	rm -rf docker/app/application-docker.yaml
	rm -rf docker/nginx/html

	cp $(JAVA_PROJECT_DIR)/target/*.jar ./docker/app/app.jar
	cp -r $(VITE_PROJECT_DIR)/dist ./docker/nginx/html

	@echo "Building Docker image using Buildx..."
	@docker buildx build --platform linux/amd64 -t $(IMAGE_NAME):$(TAG) -f $(DOCKERFILE_PATH) .

.PHONY: run
run:
	@echo "Running Docker container..."
	@docker-compose -f docker/docker-compose.yml up -d

.PHONY: stop
stop:
	@echo "Stopping Docker container..."
	@docker-compose -f docker/docker-compose.yml down -v

.PHONY: restart
restart: stop run
	@echo "Restarting Docker container..."

